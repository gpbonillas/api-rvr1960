package org.bible.api.rvr1960.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "versiculos")
@NamedQueries({
        @NamedQuery(name = "Verse.findAll", query = "SELECT v FROM Verse v"),
        @NamedQuery(name = "Verse.findByBookAndChapter",
                query = "SELECT v FROM Verse v WHERE v.book.id = :bookID AND v.chapter = :chapter AND v.verseNumber = :verseNumber")})
public class Verse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ver_id")
    private Integer id;
    @Column(name = "bib_id")
    private Short bibleID;
    @NotNull
    @JoinColumn(name = "lib_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    @Column(name = "ver_cap")
    private Short chapter;
    @Column(name = "ver_numver")
    private Short verseNumber;
    @Size(max = 2147483647)
    @Column(name = "ver_txt")
    private String verseText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getBibleID() {
        return bibleID;
    }

    public void setBibleID(Short bibleID) {
        this.bibleID = bibleID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Short getChapter() {
        return chapter;
    }

    public void setChapter(Short chapter) {
        this.chapter = chapter;
    }

    public Short getVerseNumber() {
        return verseNumber;
    }

    public void setVerseNumber(Short verseNumber) {
        this.verseNumber = verseNumber;
    }

    public String getVerseText() {
        return verseText;
    }

    public void setVerseText(String verseText) {
        this.verseText = verseText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verse)) {
            return false;
        }
        Verse other = (Verse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bible.api.rvr1960.entity.Verse[ id=" + id + " ]";
    }
}
