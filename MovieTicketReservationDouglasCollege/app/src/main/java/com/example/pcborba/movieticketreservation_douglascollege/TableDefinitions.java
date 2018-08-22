/**
 * Class that contain methods to create and delete tables on the database
 */
package com.example.pcborba.movieticketreservation_douglascollege;

/**
 * Created by offcampus on 11/22/2017.
 */

public class TableDefinitions {

    public static final String SQL_CREATE_MOVIE =
            "CREATE TABLE MOVIE (id integer primary key, name text, description text, url text )";

    public static final String SQL_CREATE_ROOM =
            "CREATE TABLE ROOM (id integer primary key, number text, seat_status integer)";

    public static final String SQL_CREATE_TICKET =
            "CREATE TABLE TICKET (id integer primary key, sessionID integer, price text, seat_number integer, paymentDate text, receiptCode text, roomID text)";

    public static final String SQL_CREATE_SESSION =
            "CREATE TABLE MOVIE_SESSION (id integer primary key, movieID integer, roomID integer, sessionDate text, sessionTime text, seats text)";

    public static final String SQL_DELETE_MOVIE =
            "DROP TABLE IF EXISTS MOVIE";

    public static final String SQL_DELETE_ROOM =
            "DROP TABLE IF EXISTS ROOM";

    public static final String SQL_DELETE_SESSION =
            "DROP TABLE IF EXISTS MOVIE_SESSION";

    public static final String SQL_DELETE_TICKET =
            "DROP TABLE IF EXISTS TICKET";



}
