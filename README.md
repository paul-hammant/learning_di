# Rules:

1. Coding may happen via Pairing **only**. I'm expecting ThoughtWorkers, but there is no reason that client devs can't be dragged in too (as long as it's in lunch-hours etc).

2. Only Paul can raise feature requests (issues). Meaning float them to him first .. as he's unveiling them one or two at a time.

3. Lets do this in a single GH repo without forking - folks should let me know they're going to commit, and I'll add them to the project. If alumni of Midvale School For The Gifted, don't tell Paul the corresponding GitHub IDs.

4. AppEngine and Android safe is a requirement.

# Glossary:

* **Inversion of Control (IoC)** - the encompassing science of how/when to lace components together.

* **Dependency Injection (DI)** - A style of IoC that deals with POJOs and uses constructors/setters to pass dependencies into instances that need them.

* **Containers** - In the context of DI, these are containers that would handle general purpose injection situations. They always your reflection to work out what magic is required.  We're not making a container in this project, but you can read about PicoContainer, Guice and Yadic if you like.

* **Application, Session, Request Scopes** - the types of component, with hints as to their lifecycle. No other naming will be used.
