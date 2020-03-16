# The Flavor Pairer

Many sites discuss what ingredients go with what, but they are the traditional and standard pairings. They are guaranteed to work, but not interesting; not something that pushes our palate. The Flavor Pairier builds lists of flavor affinities that dives into each of the ingredients to find more complex and interesting pairings.

For example: Zucchini
Typically first-level pairings:

`Basil, goat cheese, mozzarella cheese, chives, coriander, dill, garlic, olive oil, thyme, eggplant`

What pairs with each of these ingredients? Second-level pairings:

`Allspice, apples, bacon, fennel, mushrooms, nutmeg, pears, pork, tarragon`

We must go deeper. Third-level ingredients:

`Anise hyssop, bok choy, couscous, madeira, mint, rosemary, salmon, sea bass, white sesame seeds, veal`

Now we can start becoming a bit more creative and stop approaching a plate so one dimensional.

## Warning
The site doesn't work yet, but you know, that is coming along. The core backbone is in place, just need to build it up!

# Requirements
* Java 11
* Maven 3.6.3

# Installing
The project is mavenized, run the below to install.

`mvn clean install`

# Running the Tests
They are junits. Just run them. 

# Deployment
Currently set up for Heroku. 

# Local Deployment
Install the Heroku CLI. Install the jar locally and start heroku with the below commands. It should bind to localhost:8080.

`mvn clean install`

`heroku local web -f Procfile.local`
