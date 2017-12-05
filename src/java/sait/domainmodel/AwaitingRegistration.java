/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.domainmodel;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 733196
 */
@Entity
@Table(name = "awaiting_registration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AwaitingRegistration.findAll", query = "SELECT a FROM AwaitingRegistration a")
    , @NamedQuery(name = "AwaitingRegistration.findByHashed", query = "SELECT a FROM AwaitingRegistration a WHERE a.hashed = :hashed")
    , @NamedQuery(name = "AwaitingRegistration.findByUserID", query = "SELECT a FROM AwaitingRegistration a WHERE a.userID = :userID")})
public class AwaitingRegistration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Hashed")
    private String hashed;
    @Id
    @Basic(optional = false)
    @Column(name = "UserID")
    private String userID;
    @JoinColumn(name = "UserID", referencedColumnName = "Username", insertable = false, updatable = false)
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    private User user;

    public AwaitingRegistration() {
    }

    public AwaitingRegistration(String userID) {
        this.userID = userID;
    }

    public AwaitingRegistration(String userID, String hashed) {
        this.userID = userID;
        this.hashed = hashed;
    }

    public String getHashed() {
        return hashed;
    }

    public void setHashed(String hashed) {
        this.hashed = hashed;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AwaitingRegistration)) {
            return false;
        }
        AwaitingRegistration other = (AwaitingRegistration) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sait.domainmodel.AwaitingRegistration[ userID=" + userID + " ]";
    }
    
}
