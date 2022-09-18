# piano-practice

<details>
  <summary>Click here if you want to read stuff</summary>
  
Let me start this off by saying that I am not a professional pianist. I have no formal training and never thought that I would ever be playing piano until  I got my hands on Yamaha PSR 172. I got it completely for free from a manager of an Irish restaurant I used to work at back in Chicago. Thanks again, Massie!

Anyway, fast forward a few months and I realised that I am hitting the limit with the number of keys available when learning classical pieces. So I figured it was time for an upgrade. The second keyboard I got and the first one I paid for was Yamaha YPG 235. There definitely was a difference in sound but more importantly, I could now learn more complex pieces which required playing in more octaves. I realized that I started hitting the limits of the keyboard only when I got a chance to play on the real upright acoustic piano. A few things concerned me: 1) lack of pedal on my keyboard, 2) the semi-weighted keys on this keyboard were too soft and it was often the case that I would learn something on this keyboard only not to be able to play it on an acoustic piano. Muscle memory would not factor in for how much harder I would have to hit the keys on an acoustic piano. 3) THE SOUND. I really wanted something that sounded closer to the real piano. 

After thorough research, I set my eyes on Kawai-ES110 and got it in July 2018. Ever since I got it I loved everything about it.

In 2020 when we got hit with COVID I figured it was a good time to start taking piano lessons online. As a software engineer working on a data team, I wanted to quantify my efforts somehow. I had a list of pieces I wanted to learn, my teachers had a list of exercises I needed to do. Don't get me wrong - playing piano is fun and all but not when you have Carl Czerny assignments to do. It is only fun when you do it at your leisure and when you don't have a deadline to get something done. 

At the very least I wanted to hold myself accountable for practicing consistently. That's when an idea came to me to keep track of my practice time. Since I had a digital piano, I bought a midi interface and wrote some code which eventually ended up being a Telegram bot

</details>

This repository contains 3 modules, let me do a quick rundown of them:

`midi-listener` - is a Spring Boot Java application running on a local server physically connected to the digital piano via the Midi interface. Whenever a key is pressed or released, the data is sent to a topic via Web Sockets in real-time as well as queued to be sent to a remote RDS Postgres database in AWS.

`ui-piano-visualization` is a Vue JS application which is listening to the messages midi-listener sends to a topic via Web Sockets. It displays in real time what keys are pressed on the piano. I wrote it after starting online piano lessons. I found it useful to share my screen with this Vue JS app running so the teacher could clearly see what keys I am pressing and when. Sure, video is also there but one couldn't see what keys are pressed as clearly 100% of the time. 

Eventually, I got tired of opening Data Grip to check the practice time and figured it was time to create a web service I could interact with via a telegram bot

`backend` - is a Spring Boot Java application that does 2 things: 1) it runs transformations on the data fed from the digital piano into RDS to create several aggregate tables in the database for more performant queries 2) it interacts with the Telegram Bot API by instantiating a "Long Polling" service and responding to the messages sent to the bot.

Telegram bot is here: https://t.me/gregory_piano_practice_bot

