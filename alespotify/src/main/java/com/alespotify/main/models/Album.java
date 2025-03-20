package com.alespotify.main.models;

import java.util.ArrayList;
import java.util.Date;

public class Album {
    private String name;
    private String image;
    private ArrayList<Song> songs;
    private Artist artist;
    private Date fecha;

    public Album() {
    }

    public Album(String name, String image, ArrayList<Song> songs, Artist artist, Date fecha) {
        this.name = name;
        this.image = image;
        this.songs = songs;
        this.artist = artist;
        this.fecha = fecha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}