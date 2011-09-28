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

package org.androidaalto.droidkino.xml;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.beans.MovieSchedule;
import org.androidaalto.droidkino.beans.MovieTrailer;
import org.androidaalto.droidkino.beans.TheatreArea;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

/**
 * A SAX implementation of the Base FinnKino XML parser
 * 
 * @author marcostong17
 * @see BaseFinnkinoParser
 * @see MovieParser
 * 
 */
public class FinnikoSAXParser extends BaseFinnkinoParser {

    /**
     * @throws MalformedURLException
     */
    public FinnikoSAXParser() throws MalformedURLException {
        super();
    }

    @Override
    public List<MovieInfo> parseMovies(String areaId) {
        final MovieInfo movieInfo = new MovieInfo();
        final MovieTrailer movieTrailer = new MovieTrailer();
        final List<MovieInfo> movies = new ArrayList<MovieInfo>();

        RootElement root = new RootElement(EVENTS);
        Element event = root.getChild(EVENT);
        Element images = event.getChild(IMAGES);
        Element videos = event.getChild(VIDEOS);
        Element eventVideo = videos.getChild(EVENT_VIDEO);
        
       
        event.setEndElementListener(new EndElementListener() {

            @Override
            public void end() {
                movies.add(movieInfo.copy());
            }
        });
        
        eventVideo.setEndElementListener(new EndElementListener() {

            @Override
            public void end() {
                movieInfo.addMovieTrailer(movieTrailer.copy());
            }
        });

        event.getChild(ID).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieInfo.setEventId(body);
            }
        });
        
        event.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieInfo.setTitle(body);
            }
        });

        event.getChild(ORIGINAL_TITLE).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        movieInfo.setOriginalTitle(body);
                    }
                });

       event.getChild(PRODUCTION_YEAR).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setProductionYear(body);
                   }
               });
        
       event.getChild(LENGTH_IN_MINUTES).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setLenghtInMinutes(body);
                   }
               });
       
       event.getChild(DATE_LOCAL_RELEASE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setDtLocalRelease(body);
                   }
               });
       
       event.getChild(RATING_LABEL).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setRatingLabel(body);
                   }
               });
       
       event.getChild(LOGAL_DISTRIBUTOR_NAME).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setLocalDistributorName(body);
                   }
               });
       
       event.getChild(GLOBAL_DISTRIBUTOR_NAME).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setGlobalDistributorName(body);
                   }
               });
       
       event.getChild(GENRES).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setGenres(body);
                   }
               });
       
       event.getChild(SYNOPSIS).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setSynopsis(body);
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
       
       eventVideo.getChild(TITLE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieInfo.setTitle(body);
                   }
               });
       eventVideo.getChild(LOCATION).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieTrailer.setLocation(body);
                   }
               });
       eventVideo.getChild(THUMBNAIL_LOCATION).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieTrailer.setThumbnailLocation(body);
                   }
               });
       
       eventVideo.getChild(MEDIA_RESOURCE_FORMAT).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieTrailer.setMediaResourceFormat(body);
                   }
               });
       
       eventVideo.getChild(MEDIA_RESOURCE_SUB_TYPE).setEndTextElementListener(
               new EndTextElementListener() {

                   @Override
                   public void end(String body) {
                       movieTrailer.setMediaResourceSubType(body);
                   }
               });
       
        try {
            StringBuffer urlStringBuffer = new StringBuffer(BASE_FINN_FINO_URL);
            urlStringBuffer.append(EVENTS);
            if (areaId != null) {
                urlStringBuffer.append("?");
                urlStringBuffer.append(PARAM_AREA);
                urlStringBuffer.append("=");
                urlStringBuffer.append(areaId);                                
            }
            URL url = new URL(urlStringBuffer.toString());
            Xml.parse(this.getInputStream(url), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return movies;

    }

    @Override
    public List<TheatreArea> parseAreas() {
        
        final TheatreArea theatreArea = new TheatreArea();
        final List<TheatreArea> theatres = new ArrayList<TheatreArea>();
        
        RootElement root = new RootElement(THEATRE_AREAS);
        Element area = root.getChild(THEATRE_AREA);
        area.setEndElementListener(new EndElementListener() {

            @Override
            public void end() {
                theatres.add(theatreArea.copy());
            }
        });
        
        
        area.getChild(ID).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                theatreArea.setId(body);
            }
        });
        
        area.getChild(NAME).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                theatreArea.setName(body);
            }
        });
        
        
        try {
            URL url = new URL(BASE_FINN_FINO_URL + THEATRE_AREAS);
            Xml.parse(this.getInputStream(url), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return theatres;
    }

    
    
    @Override
    public List<MovieSchedule> parseSchedules(String areaId, String date, String eventId) {
        final MovieSchedule movieSchedule = new MovieSchedule();
        final List<MovieSchedule> schedules = new ArrayList<MovieSchedule>();

        RootElement root = new RootElement(SCHEDULE);
        Element shows = root.getChild(SHOWS);
        Element show = shows.getChild(SHOW);
        Element images = show.getChild(IMAGES);
        
        show.setEndElementListener(new EndElementListener() {

            @Override
            public void end() {
                schedules.add(movieSchedule.copy());
            }
        });
        
        
        show.getChild(EVENT_ID).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setEventId(body);
            }
        });
        
        
        show.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setTitle(body);
            }
        });
        
        show.getChild(LENGTH_IN_MINUTES).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setLengthInMinutes(Integer.parseInt(body));
            }
        });
        
        show.getChild(THEATRE_ID).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setTheatreId(body);
            }
        });
        
        show.getChild(THEATRE).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setTheatre(body);
            }
        });

        show.getChild(THEATRE_AUDITORIUM).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setTheatreAuditorium(body);
            }
        });

        
        show.getChild(DTTM_SHOW_START).setEndTextElementListener(new EndTextElementListener() {

            @Override
            public void end(String body) {
                movieSchedule.setDttmShowStart(body);
            }
        });
        
        
        
        show.getChild(PRESENTATION_METHOD_AND_LANGAUGE).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        if (body.startsWith("3D")) {
                            movieSchedule.set3D(true);
                            if (body.substring(2).length() > 0) {
                                movieSchedule.setLanguage(body.substring(3));
                            }
                        }
                        else 
                        {
                            movieSchedule.set3D(false);
                            movieSchedule.setLanguage(body);
                        }
                    }
                });
        
        images.getChild(EVENT_SMALL_IMAGE_PORTRAIT).setEndTextElementListener(
                new EndTextElementListener() {

                    @Override
                    public void end(String body) {
                        movieSchedule.setEventSmallImagePortrait(body);
                    }
                });
        
        try {
            StringBuffer urlStringBuffer = new StringBuffer(BASE_FINN_FINO_URL);
            urlStringBuffer.append(SCHEDULE);
            if (areaId != null || date != null) {
                urlStringBuffer.append("?");
                
                urlStringBuffer.append(PARAM_AREA);
                urlStringBuffer.append("=");
                urlStringBuffer.append(areaId);                                
            
                urlStringBuffer.append("&");
                
                urlStringBuffer.append(PARAM_DATE);
                urlStringBuffer.append("=");
                urlStringBuffer.append(date);                                
            }
            URL url = new URL(urlStringBuffer.toString());
            Xml.parse(this.getInputStream(url), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return schedules;
    }

}
