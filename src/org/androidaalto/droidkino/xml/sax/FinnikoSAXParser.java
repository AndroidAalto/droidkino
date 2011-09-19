/*******************************************************************************

Copyright: 2011 Android Aalto Community This file is part of
Droidkino. Droidkino is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 2 of the
License, or (at your option) any later version. Droidkino is
distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
for more details. You should have received a copy of the GNU General
Public License along with Droidkino; if not, write to the Free
Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
02110-1301 USA

 ******************************************************************************/

package org.androidaalto.droidkino.xml.sax;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.androidaalto.droidkino.MovieInfo;
import org.androidaalto.droidkino.xml.BaseFinnkinoParser;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

public class FinnikoSAXParser extends BaseFinnkinoParser {

    /**
     * @throws MalformedURLException
     */
    public FinnikoSAXParser() throws MalformedURLException {
        super();
    }

    @Override
    public List<MovieInfo> parse() {
        final MovieInfo movieInfo = new MovieInfo();
        final List<MovieInfo> movies = new ArrayList<MovieInfo>();

        RootElement root = new RootElement(SCHEDULE);
        Element shows = root.getChild(SHOWS);
        Element show = shows.getChild(SHOW);
        Element images = show.getChild(IMAGES);
        show.setEndElementListener(new EndElementListener() {

            @Override
            public void end() {
                movies.add(movieInfo.copy());
            }
        });

        show.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieInfo.setTitle(body);
            }
        });

        show.getChild(DTTM_SHOW_START).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieInfo.setDttmShowStart(body);
            }
        });

        show.getChild(THEATRE_AUDITORIUM).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        movieInfo.setTheatreAuditorium(body);
                    }
                });
        
        show.getChild(ORIGINAL_TITLE).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        movieInfo.setOriginalTitle(body);
                    }
                });
        
       show.getChild(THEATRE).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        movieInfo.setTheatre(body);
                    }
                });

       show.getChild(PRODUCTION_YEAR).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setProductionYear(body);
                   }
               });
        
       show.getChild(LENGTH_IN_MINUTES).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setLenghtInMinutes(body);
                   }
               });
       
       show.getChild(DATE_LOCAL_RELEASE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setDtLocalRelease(body);
                   }
               });
       
       show.getChild(RATING_LABEL).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setRatingLabel(body);
                   }
               });
       
       show.getChild(GENRES).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setGenres(body);
                   }
               });
        
       show.getChild(PRESENTATION_METHOD_AND_LANGAUGE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       if (body.startsWith("3D")) {
                           movieInfo.set3D(true);
                           if (body.substring(2).length() > 0) {
                               movieInfo.setLanguage(body.substring(3));
                           }
                       }
                       else 
                       {
                           movieInfo.set3D(false);
                           movieInfo.setLanguage(body);
                       }
                   }
               });
        
       images.getChild(EVENT_SMALL_IMAGE_PORTRAIT).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setEventSmallImagePortrait(body);
                   }
               });
       
       images.getChild(EVENT_LARGE_IMAGE_PORTRAIT).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setEventLargeImagePortrait(body);
                   }
               });
       
       
       images.getChild(EVENT_SMALL_IMAGE_LANDSCAPE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setEventSmallImageLandscape(body);
                   }
               });
       
       images.getChild(EVENT_LARGE_IMAGE_LANDSCAPE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setEventLargeImageLandscape(body);
                   }
               });
       
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return movies;

    }

}
