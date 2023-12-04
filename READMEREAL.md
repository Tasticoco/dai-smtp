# SMTP Pranking
___
>### Authors
>Arthur Junod & Edwin Häffner
> 
>Based on [DAI lab: SMTP by Ehrensberger Juergen](https://github.com/HEIGVD-Course-DAI/dai-lab-smtp)

## Introduction
A simple SMTP client that can be used to automate sending emails to a list of email addresses.

### Disclaimer
Beware of your local laws, you might get in trouble for this ! You can read this to
have more information about the legality of doing this : [Is it legal to prank people with this ?](https://anyleads.com/can-you-get-in-trouble-for-sending-unsolicited-emails)

As this is a school project, we are not responsible for any harm you might cause
and we will use a mock SMTP server to test our program. *More information below.*

## Description

This project is a simple SMTP client that can be used to automate sending emails
to a list of email addresses. Everything is configurable, from the number of groups
you want to make to the messages you want to send and to which addresses you want to send them.
___
## How does it work and how to use it ?

### The email configuration

The emails are sent in groups, where each group has **one sender**
and the rest are **receivers** or "Victims". Every group is generated from a list of emails
that is configurable in the `configEmail.json` file. It's been set up this way for 
ease of use as JSON is a pretty well known format and it's easy to read and write into.

### The message configuration

Every messages are stored in the `configMessages.json` file. It's a simple JSON array
where each object is a message. The message has a `subject` and a `body` field. And you
can add as many messages as you want. Know that the messages are picked randomly from
this file list and if you want to send a specific message to a specific group, you can
just remove the other messages from the file and only keep the one you want to send.

### The number of groups 

The number of groups is easily setup as a command line argument. You can just run the
program with a number as an argument. If you don't specify this argument, **the default value is 5**.

### The SMTP server

As it is probably not legal to actually send bad emails to people without their consent,
it is highly recommended to use a mock SMTP server to test your program. We used [MailDev](https://github.com/maildev/maildev),
which is a mock server that allows you to observe the traffic of the emails that are sent to it.
It also has a WEB interface that allows you to see the emails that are sent to it.

#### How to set it up

You can use docker to start the server:

    docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev

This provides a website on localhost:1080 and a SMTP server on localhost:1025. And
unless you tweak this program's code, you will only be able to send emails to the port 1025.
*For obvious reasons.*

## Example of running the program

