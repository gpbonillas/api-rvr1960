package org.bible.api.rvr1960.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bible.api.rvr1960.entity.Book;
import org.bible.api.rvr1960.service.BookService;
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
@OpenAPIDefinition(info = @Info(title = "Books endpoint", version = "1.0"))
@Path("books")
public class BookREST {

    @Inject
    BookService bookService;

    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Books for id",
                    content = @Content(
                            mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
                            schema = @Schema(
                                    ref = "Books"))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No Books found for the id.")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(bookService.getAll()).build();
    }

    @Counted(
            name = "getBook",
            absolute = true,
            displayName = "get single book",
            description = "Monitor how many times getBook method was called")
    @GET
    @Path("{id}")
    public Response getProforma(@PathParam("id") Integer id) {
        Book book = bookService.findById(id);

        return Response.ok(book).build();
    }

    @Metered(name = "create-books",
            unit = MetricUnits.MILLISECONDS,
            description = "Monitor the rate events occurred",
            absolute = true)
    @POST
    public Response create(Book book) {
        bookService.create(book);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, Book book) {
        Book updateBook = bookService.findById(id);

        updateBook.setName(book.getName());

        bookService.update(updateBook);

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        Book getBook = bookService.findById(id);
        bookService.delete(getBook);
        return Response.ok().build();
    }
}
