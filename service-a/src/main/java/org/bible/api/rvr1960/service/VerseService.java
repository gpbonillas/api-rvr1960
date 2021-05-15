package org.bible.api.rvr1960.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.bible.api.rvr1960.entity.Verse;
import org.bible.api.rvr1960.exception.BibleException;

import java.util.ArrayList;
import java.util.List;

public class VerseService {

    @PersistenceContext(unitName = "rvr1960PU")
    EntityManager em;

    public List getAll() {
        List<Verse> verses = em.createNamedQuery("Verse.findAll", Verse.class).setMaxResults(100).getResultList();
        return verses != null ? verses : new ArrayList<>();
    }

    public Verse getVerseByBookAndChapter(Short bookID, Short chapter, Short verse) throws BibleException {
        Verse versicle = null;
        TypedQuery<Verse> verseQuery = em.createNamedQuery("Verse.findByBookAndChapter", Verse.class);
        verseQuery.setParameter("bookID", bookID);
        verseQuery.setParameter("chapter", chapter);
        verseQuery.setParameter("verseNumber", verse);

        try {
            List<Verse> verses = verseQuery.getResultList();

            if(!verses.isEmpty()) {
                versicle = verses.get(0);
            } else {
                throw new BibleException("Error while find verse for book id: "
                        + bookID + ", chapter: "
                        + chapter + " and verse: "
                        + verse + ". Empty List");
            }
        } catch (Exception e) {
            throw new BibleException("Error while find verse for book id: "
                    + bookID + ", chapter: "
                    + chapter + " and verse: "
                    + verse, e.getCause());
        }

        return versicle;
    }

    public Verse findById(Integer id) {
        return em.find(Verse.class, id);
    }

    @Transactional
    public void update(Verse verse) {
        em.merge(verse);
    }

    @Transactional
    public void create(Verse verse) {
        em.persist(verse);
    }

    @Transactional
    public void delete(Verse verse) {
        if (!em.contains(verse)) {
            verse = em.merge(verse);
        }
        em.remove(verse);
    }
}
