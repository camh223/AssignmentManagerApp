# Introduction

AssignmentManagerApp is an app that aims to help university students manage their assignments. It does this by
providing many useful tools to aid with management, such as providing an interface to create and view assignments.
It also stores information about assignments, including a description, the date that the assignment is due, and
the module code that the assignment belongs to.

## Design Rationale

When designing the app, I decided to mainly make use of LinearLayout due to it's ease of use. I found it very easy to organise elements into columns and rows using this layout, allowing me to effectively create forms to receive and display information to students. 

I also made use of FrameLayout and RecyclerView, due to it's efficient use of storage when listing a large number of items. Since there is no limit to how many assignments a student may wish to add to the app, it was important to ensure that storage was used effectively so that performance was effected minimally.

I decided not to make use of fragments in this app, as there are not many screens or layouts that can be reused since they all serve very different purposes.

For storage, I used a MySQL Database due to it's easy to execute queries that make the storage and processing of data extremely easy. It suited my purposes very well, as I can easily refer to an assignment using it's unique ID which made displaying data from the database an easy and lightweight process.

## Novel Features

The main novel feature of this app is the abilty to generate literature for any topic using the built-in literature API. Users can simply go onto one of their assignments, enter some keywords, and, provided that they have an internet connection, the app will list literature in JSON format so that they may access them for their research or assignments. This is a feature that other management apps do not provide, so in addition to all of the other features that this app offers, it is a very unique concept that solves a problem that many university students today face.


## Challenges

I faced many challenges as I developed this app, which limited some of the features I may have liked to implement given a larger time frame. In particular I had difficulty integrating RecyclerView with other features such as implicit and explicit intents, and content providers. While I did manage to resolve many of the issues that I faced, others impeded the development of this app significantly and reduce the time that could've been spent on developing other aspects of the app. In particular, I think the aesthetic features of the app could have been worked on further to create an interesting and engaging user interface. In addition, I also would've liked to add some gamification features to incentivise students to complete deadlines more effectively, such as a point system and a leaderboard. However, I believe the app currently serves it's purpose well and will be a useful tool for students in the future.