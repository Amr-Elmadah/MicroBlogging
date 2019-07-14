# MicroBlogging
The purpose of this repo is to follow up Clean Architecture principles by bringing them to Android, using https://sym-json-server.herokuapp.com/ API
to show up list of Authors and their posts which contain comments:<br />
  1- Authors List : All authors from server and if internet connection failed it'll show all localy stored authors.<br />
  2- Author Details : Show author details with his previous posts.<br />
  3- Post Details : Show post details with its previous comments.<br /><br />
-- this code inspired by clean code architicture by uncle pop   and Fernando Cejas (https://fernandocejas.com/2018/05/07/architecting-android-reloaded/ )<br /> <br /> <br /> <br /> 
![alt text](https://d33wubrfki0l68.cloudfront.net/6146b5d98803b226e2739514605952290f31e80e/09c0f/media/microservices-or-monolith/cleanarch.png)
<br /> <br />
<br /> <br />
**Every user story flow this  architecture**
<br /> <br />
![alt text](https://cdn-images-1.medium.com/max/1600/1*3smlPZenpAtICXdgcjuHSg.jpeg)
<br /> <br />

#### Used Technologies : <br />
* Koltin.<br />
* Retrofit:Consume Network requests .<br />
* Rxjava.<br />
* Dependency injection using Dagger 2.<br />
* Data Binding.<br />
* Material Design.<br />
* MVVM and Livedata.<br />
* Room : Local database.<br />
* Stetho : ADB Debugging.<br />
* Leak Canary : Memory leaking.<br />
* Junit 4 - Mockito : Unit testing.<br />