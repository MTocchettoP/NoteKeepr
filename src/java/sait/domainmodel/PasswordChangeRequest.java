/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.domainmodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 733196
 */
@Entity
@Table(name = "password_change_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasswordChangeRequest.findAll", query = "SELECT p FROM PasswordChangeRequest p")
    , @NamedQuery(name = "PasswordChangeRequest.findById", query = "SELECT p FROM PasswordChangeRequest p WHERE p.id = :id")
    , @NamedQuery(name = "PasswordChangeRequest.findByTime", query = "SELECT p FROM PasswordChangeRequest p WHERE p.time = :time")})
public class PasswordChangeRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @Column(name = "Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "UserID", referencedColumnName = "Username")
    @ManyToOne(optional = false)
    private User userID;

    public PasswordChangeRequest() {
    }

    public PasswordChangeRequest(String id) {
        this.id = id;
    }

    public PasswordChangeRequest(String id, Date time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
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
        if (!(object instanceof PasswordChangeRequest)) {
            return false;
        }
        PasswordChangeRequest other = (PasswordChangeRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sait.domainmodel.PasswordChangeRequest[ id=" + id + " ]";
    }
    
}
