package org.bible.api.rvr1960.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.bible.api.rvr1960.entity.Book;
import org.bible.api.rvr1960.entity.Verse;

import java.util.ArrayList;
import java.util.List;

public class VerseService {

    @PersistenceContext(unitName = "rvr1960PU")
    EntityManager em;

    public List getAll() {
        List<Verse> verses = em.createNamedQuery("Verse.findAll", Verse.class).setMaxResults(100).getResultList();
        return verses != null ? verses : new ArrayList<>();
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
