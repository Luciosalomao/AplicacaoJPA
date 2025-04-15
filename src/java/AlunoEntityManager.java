import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/**
 *
 * @author lucio.souza
 */

@Entity
@Table(name="aluno")
public class AlunoEntityManager {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;
   private String nome;

    public  AlunoEntityManager(){
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
   
   
}
