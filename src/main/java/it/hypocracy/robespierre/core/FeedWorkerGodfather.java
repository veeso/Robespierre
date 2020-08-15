/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core;

import java.util.ArrayList;
import java.util.Collections;

import it.hypocracy.robespierre.utils.Uuidv4;

/**
 * This class gives name to Feed Workers. Yes.
 */

public class FeedWorkerGodfather {
  private final ArrayList<String> names;

  public FeedWorkerGodfather(boolean useGeekNames) {
    // Initialize names
    names = new ArrayList<>();
    if (useGeekNames) {
      // Put names (> 96)
      // Tech geek stuff
      addName("Bash");
      addName("C++");
      addName("Python");
      addName("Java");
      addName("Rust");
      addName("GCC");
      addName("Yocto");
      addName("Balena");
      addName("Docker");
      addName("Maria");
      addName("EOF");
      addName("Kernel Panic");
      addName("Debian");
      addName("Github");
      addName("React");
      addName("NodeJS");
      addName("Segfault");
      addName("SIGABRT");
      addName("Kill-9");
      addName("psaux");
      addName("Redis");
      addName("Mongo");
      addName("pip");
      addName("CMake");
      addName("Gitignore");
      addName("Linux");
      addName("Fedora");
      addName("Redux");
      addName("Systemd");
      addName("Qwerty");
      addName("Regex");
      addName("Manjaro");
      addName("apt-get");
      addName("sudo");
      addName("-fpedantic");
      // Geek stuff
      // Sonic
      addName("Amy Rose");
      addName("Sonic");
      addName("Mr. Eggman");
      // TF2
      addName("Pootis Spencer");
      addName("The G-Man");
      addName("Red Engineer");
      addName("Rancho Relaxo");
      addName("Herr Doktor");
      // Mario
      addName("Mario");
      addName("Luigi");
      addName("Toad");
      addName("Peach");
      addName("Daisy");
      // Zelda
      addName("Link");
      addName("Zelda");
      addName("Ganondorf");
      // Memes
      addName("Rest in pepperoni");
      addName("Moon Emoji");
      addName("Master Hand");
      addName("Crazy Hand");
      addName("Konty");
      addName("Mickey");
      addName("Omar");
      addName("Globglogabgalab");
      addName("Shrek");
      addName("Caramelldansen");
      addName("Doge Shibe");
      addName("Harambe");
      addName("Pingu");
      addName("Gabe");
      addName("Doggo");
      addName("Big Shaq");
      addName("Poppy");
      addName("Aimbot");
      addName("Wallhack");
      addName("Paint.exe");
      addName("Animoji");
      addName("Nyan Cat");
      addName("Mr. Sandman");
      // minecraft
      addName("Steve");
      addName("Herobrine");
      addName("Noteblock");
      addName("Pink Sheep");
      // TV Series
      addName("Bojack");
      addName("Diane Nguyen");
      addName("Mr. Peanutbutter");
      addName("Princess Carolyn");
      addName("Piper Chapman");
      addName("Rick");
      addName("Morty");
      addName("Mr. Meeseeks");
      addName("Abed Nadir");
      // Pokemon
      addName("Ditto");
      addName("Pikachu");
      addName("Clefairy");
      addName("Sylveon");
      addName("Latias");
      addName("Latios");
      addName("Mew");
      addName("Mewtwo");
      // Animal Crossing
      addName("Tom Nook");
      addName("Isabelle");
      addName("Tortimer");
      addName("K.K. Slider");
      // Subnautica
      addName("Reaper Leviathan");
      addName("Mesmer");
      addName("Warper");
      addName("Peeper");
      // Overwatch
      addName("Winston");
      addName("D.Va");
      addName("Tracer");
      addName("Genji");
      // Other stuff
      addName("Barbie Girl");
      addName("Pizza");
      addName("Coconut");
      addName("Seitan");
      addName("Tofu");
      addName("Curry");
      addName("Pineapple");
      addName("Long Island");
      addName("Midori");
      addName("Pina Colada");
      addName("P.Y.T.");
      // It's me
      addName("Veeso");
    }
    // Then shuffle list
    Collections.shuffle(names);
  }

  /**
   * <p>
   * Baptize worker
   * </p>
   * 
   * @return String
   */

  public String baptize() {
    // If names has length 0, return 'UUID'
    if (names.size() == 0) {
      return new Uuidv4().toString();
    }
    // Get first element in list and remove it from the list
    return names.remove(0);
  }

  /**
   * <p>
   * Add name to names
   * </p>
   * 
   * @param name
   */

  private void addName(String name) {
    this.names.add(name);
  }

}
