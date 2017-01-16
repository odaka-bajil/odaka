package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Schedule extends Model {
	public String title;
	public String date;

}
