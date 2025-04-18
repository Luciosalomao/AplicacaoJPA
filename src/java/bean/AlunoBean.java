package bean;

import db.AlunoEntityManager;
import db.DatabaseOperations;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucio.souza
 */
@Named("alunoBean")
@RequestScoped
public class AlunoBean implements Serializable{
    private int id;
    private String nome;
    private String editIdAluno;

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

    public String getEditIdAluno() {
        return editIdAluno;
    }

    public void setEditIdAluno(String editIdAluno) {
        this.editIdAluno = editIdAluno;
    }
    
    public List listaAlunosDB() {
        return DatabaseOperations.getDetalhesAlunos();
    }
    
    public String cadastrarNovoAluno(AlunoBean alunoBean){
        return DatabaseOperations.cadastraNovoAluno(alunoBean.getNome());
    }
    
    public String deletaAlunoPeloId(int alunoId){
        return DatabaseOperations.deleteAluno(alunoId);
    }
    
    public String atualizarAluno(AlunoBean alunoBean) {
        return DatabaseOperations.atualizarDadosAluno(alunoBean.getId(), alunoBean.getNome());
    }
    
    public String editarAlunoPeloId() {
        editIdAluno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAluno");
        return "editaraluno.xhtml?idAluno=" + editIdAluno;
    }
    

    public void carregarAluno() {
        String idParam = FacesContext.getCurrentInstance()
            .getExternalContext()
            .getRequestParameterMap()
            .get("idAluno");

        System.out.println("Valor manual do parâmetro idAluno: " + idParam);

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int alunoId = Integer.parseInt(idParam);
                AlunoEntityManager aluno = DatabaseOperations.getAlunoPorId(alunoId);
                if (aluno != null) {
                    this.nome = aluno.getNome();
                    this.id = aluno.getId(); // se quiser usar o ID depois
                    System.out.println("Aluno carregado: " + nome);
                } else {
                    System.out.println("Aluno não encontrado para ID: " + alunoId);
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido na URL: " + idParam);
            }
        } else {
            System.out.println("Parâmetro idAluno não foi encontrado na URL.");
        }
    }
}
