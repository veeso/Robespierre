/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
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
      addName("Mr Eggman");
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
      addName("Paintexe");
      addName("Animoji");
      addName("Nyan Cat");
      addName("Mr Sandman");
      // minecraft
      addName("Steve");
      addName("Herobrine");
      addName("Noteblock");
      addName("Pink Sheep");
      // TV Series
      addName("Bojack");
      addName("Diane Nguyen");
      addName("Mr Peanutbutter");
      addName("Princess Carolyn");
      addName("Piper Chapman");
      addName("Rick");
      addName("Morty");
      addName("Mr Meeseeks");
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
      addName("KK Slider");
      // Subnautica
      addName("Reaper Leviathan");
      addName("Mesmer");
      addName("Warper");
      addName("Peeper");
      // Overwatch
      addName("Winston");
      addName("DVa");
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
      addName("PYT");
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
