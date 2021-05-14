package org.bible.api.rvr1960.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bible.api.rvr1960.entity.Verse;
import org.bible.api.rvr1960.service.VerseService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@ApplicationScoped
@OpenAPIDefinition(info = @Info(title = "Verses endpoint", version = "1.0"))
@Path("verses")
public class VerseREST {

    @Inject
    VerseService verseService;

    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Verses for id",
                    content = @Content(
                            mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
                            schema = @Schema(
                                    ref = "Verses"))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No Verses found for the id.")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(verseService.getAll()).build();
    }

    @Counted(
            name = "getVerse",
            absolute = true,
            displayName = "get single verse",
            description = "Monitor how many times getVerse method was called")
    @GET
    @Path("{id}")
    public Response getVerse(@PathParam("id") Integer id) {
        Verse verse = verseService.findById(id);

        return Response.ok(verse).build();
    }

    @Metered(name = "create-verses",
            unit = MetricUnits.MILLISECONDS,
            description = "Monitor the rate events occurred",
            absolute = true)
    @POST
    public Response create(Verse verse) {
        verseService.create(verse);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, Verse verse) {
        Verse updateVerse = verseService.findById(id);

        updateVerse.setVerseText(verse.getVerseText());
        updateVerse.setVerseNumber(verse.getVerseNumber());
        updateVerse.setChapter(verse.getChapter());

        verseService.update(updateVerse);

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        Verse getVerse = verseService.findById(id);
        verseService.delete(getVerse);
        return Response.ok().build();
    }
}
