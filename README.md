MultiProject Silhouette Slick Seed Template
===========================================

This is a fork a the fork of the official Silhouette Seed project. 
This fork shows how to work with Silhouette & Slick as a subproject in a multiproject SBT project.
If you want to have a first look at Silhouette, I suggest you have a look at the [official project](https://github.com/mohiva/play-silhouette-seed).

The Silhouette Seed project is an Activator template which shows how [Silhouette](https://github.com/mohiva/play-silhouette) can be implemented in a Play Framework application. 
It's a starting point which can be extended to fit your needs.
It uses the [play-slick](https://github.com/playframework/play-slick) library for database access.

## Example

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

(The "Build App" phase will take a few minutes)

Currently, there is no live example of this template.

## Features

* Sign Up
* Sign In (Credentials)
* Social Auth (Facebook, Google+, VK, Twitter, Xing, Yahoo)
* Two-factor authentication with Clef
* Dependency Injection with Guice
* Publishing Events
* Avatar service
* Remember me functionality
* [Security headers](https://www.playframework.com/documentation/2.4.x/SecurityHeaders)
* [CSRF Protection](https://www.playframework.com/documentation/2.4.x/ScalaCsrf)
* play-slick database access

## Documentation

Consult the [Silhouette documentation](http://silhouette.mohiva.com/docs) for more information. If you need help with the integration of Silhouette into your project, don't hesitate and ask questions in our [mailing list](https://groups.google.com/forum/#!forum/play-silhouette) or on [Stack Overflow](http://stackoverflow.com/questions/tagged/playframework).

### Slick

The template stores all authentication information in a database via [Slick](http://slick.typesafe.com/) 
It uses an in memory [H2](www.h2database.com/) database by default.

In order to use another database supported by Slick, you need to change the driver in your application.conf and add the corresponding JDBC driver to your dependencies. 
The [Play Slick documentation](https://www.playframework.com/documentation/2.4.x/PlaySlick) has more information about database configuration.

## Activator

This project template is also
[hosted at typesafe](https://typesafe.com/activator/template/play-silhouette-slick-seed).

# License

The code is licensed under [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
