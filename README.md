# Rules:

1. Coding may happen via Pairing **only**

2. Only Paul can raise Feature Requests (issues). Meaning float them to him first .. as he's unveiling them one or two at a time.

# Glossary:

* IoC - Inversion of Control - the encompassing science of how to lace together components

* Dependency Injection (DI) - A style of IoC that deals with POJOs and uses constructors/setters to pass dependencies into instances that need them

* Containers - In the context of DI, these are containers that would handle general purpose injection situations. They always your reflection to work out what magic is required.  We're not making a container in this project, but you can read about PicoContainer, Guice and Yadic if you like.

* Application, Session, Request Scopes - the types of component, with hints as to their lifecycle. No other naming will be used.
