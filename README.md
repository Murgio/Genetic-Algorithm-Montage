# Genetic Algorithm for Self-Referential Image Approximation
[![Build Status](https://travis-ci.org/Murgio/Genetic-Algorithm-Montage.svg?branch=master)](https://travis-ci.org/Murgio/Genetic-Algorithm-Montage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/78889379dbe94fdf8a9c44746e13bd6b)](https://www.codacy.com/app/muriz-se/Genetic-Algorithm-Montage?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Murgio/Genetic-Algorithm-Montage&amp;utm_campaign=Badge_Grade)

<div align="center">
  <img src="images/mona.png" width="250px"/>
  <img src="images/120000 gens.jpg" width="250px"/>
  <p>Mona Lisa after 120'000 generations approximated with 260 individuals.</p>
</div>


## Introduction
One approach to solve the image matching problem is with some *stochastic optimization* approach, in which the search for the optimal solution involves randomness in some constructive way. If S denotes the (finite) set of all possible solutions, the task we consider is to maximize or minimize the *objective function* <img alt="latex" src="images/latex/1.png?invert_in_darkmode" align=middle width="64.75817pt" height="23.307pt"/>. In the case of maximization, on which we focus here, the problem is to find a *configuration* <img alt="latex" src="images/latex/2.png?invert_in_darkmode" align=middle width="63.65817pt" height="23.407pt"/> which satisfies

<p align="center"><img alt="latex" src="images/latex/3.png" width="265px"></p>


## Genetic Algorithm
Genetic algorithms operate on a set of individuals (solutions) which form a population for a determined generation, then either two individuals are selected and combined in a crossover operation or each individual is mutated.

We might refer to an approximate solution as a "candidate", or the solution's "DNA".

A genetic algorithm tries to solve the image matching problem by starting with a random population of 260 sets of DNA consisting in form of genes with a length of 5. A fitness function is used to identify the best and worst DNA.
To get a measure of how similar two images are, we calculate the root-mean-square (RMS) value of the difference between the images. If the images are exactly identical, this value is zero.

<p align="center"><img alt="latex" src="images/latex/4.png" width="230px"></p>

Crossover and mutations are randomly performed in order to generate new solutions.
Then, based on a selection criterion, the strongest individuals (those with the best value of a performance metric) survive and remain for the next generation. 

The process is repeated until some stopping conditions are fulfilled.
In order to perform the selection of the individuals in the GA a fitness value needs to be defined. This fitness value measures the quality of the individuals and enables them to be compared.

## Procedure

<div align="center">
  <img src="images/will_genetic.gif" width="280px"/>
  <p>Computation time: 43 min @ Intel i9 9900k</p>
  <img src="images/mona_output.gif" width="260px"/>
  <p>Computation time: 59 min @ Intel i9 9900k</p>
</div>

## Results

Input Face             |   ~100'000 generations | Fitness Score
:-------------------------:|:-------------------------:|:-------------------------:
<img src="images/mona.png" width="150"> |  <img src="images/120000 gens.jpg" width="150"> | Mona Lisa: **93.071%**
<img src="images/girl.jpg" width="160"> | <img src="images/74000%20gens.jpg" width="160"> | Girl with a Pearl Earring: **84.095%**
<img src="images/will.jpg" width="159"> |  <img src="images/96600%20gens.jpg" width="159"> | Will Smith: **85.863%**
<img src="images/elon.jpg" width="160"> | <img src="images/35000%20gens.jpg" width="160"> | Elon Musk: **81.632%**
<img src="images/rowan.jpg" width="170"> | <img src="images/106000%20gens.jpg" width="170"> | Rowan Atkinson: **89.364%**
<img src="images/mkbhd.png" width="140"> | <img src="images/120200%20gens.jpg" width="140"> | Marques Brownlee: **90.349%**


## Misc

Pseudo Code             |   Flow chart
:-------------------------:|:-------------------------:
<img src="images/pseudo_code.png" width="330">  |  <img src="images/flow.png" width="330">

## License

**MIT License**

[Muriz Serifovic](https://muriz.me)