package org.bible.api.rvr1960.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.bible.api.rvr1960.entity.Book;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class BookService {

    @PersistenceContext(unitName = "rvr1960PU")
    EntityManager em;

    public List getAll() {
        List<Book> books = em.createNamedQuery("Book.findAll", Book.class).getResultList();
        return books != null ? books : new ArrayList<>();
    }

    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }

    @Transactional
    public void update(Book book) {
        em.merge(book);
    }

    @Transactional
    public void create(Book book) {
        em.persist(book);
    }
    @Transactional
    public void delete(Book book) {
        if (!em.contains(book)) {
            book = em.merge(book);
        }
        em.remove(book);
    }
}
