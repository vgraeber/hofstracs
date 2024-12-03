import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

class l08 {
  public static Optional<String> load_dna(String filename) { 
    Optional<String> answer = Optional.empty();
    try {
      Scanner br = new Scanner(new File(filename));
      answer = Optional.of(br.nextLine());
      br.close();
    } catch (IOException ie) {}
    return answer;
  }
  public static void main(String[] args) {
    String mother = load_dna("mother.dna").orElse("");
    String father = load_dna("father.dna").orElse("");
    String arman = load_dna("arman.dna").orElse("");
    String artyan = load_dna("artyan.dna").orElse("");
    scheme3 sch = new scheme3(arman, father);
    sch.printAlignment();
  }
}