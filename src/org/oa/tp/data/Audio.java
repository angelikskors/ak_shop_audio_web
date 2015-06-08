package org.oa.tp.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Audio {
    @SerializedName( "id")
    private final long id;
    @SerializedName("name")
    private final String name;
    @SerializedName("author_id")
    private final long authorId;
    @SerializedName("duration")
    private final int duration;
    @Expose
    private Author author;
    @SerializedName("price")
    private double  price;
    @SerializedName("genre_id")
    private long genreId;
    @Expose
    private Genre genre;
    @SerializedName("album_id")
    private long albumId;
    @Expose
    private Album album;

    public Audio(long id, String name, long authorId, double price, long genreId, int duration,long albumId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.price = price;
        this.genreId = genreId;
        this.duration = duration;
        this.albumId=albumId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAuthorId() {
        return authorId;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audio audio = (Audio) o;

        return id == audio.id;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + duration;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (genreId ^ (genreId >>> 32));
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        result = 31 * result + (album != null ? album.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author_Id=" + authorId +

                ", duration=" + duration +
                ", price=" + price +
                ", genre_Id=" + genreId +

                ", album_Id=" + albumId +

                '}';
    }
}
