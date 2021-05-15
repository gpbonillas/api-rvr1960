package org.bible.api.rvr1960.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "libros")
@NamedQueries({@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "lib_id")
    private Short id;
    @Column(name = "lib_tes")
    private Character testament;
    @Size(max = 100)
    @Column(name = "lib_nom")
    private String name;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Character getTestament() {
        return testament;
    }

    public void setTestament(Character testament) {
        this.testament = testament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bible.api.rvr1960.entity.Book[ id=" + id + " ]";
    }
}
