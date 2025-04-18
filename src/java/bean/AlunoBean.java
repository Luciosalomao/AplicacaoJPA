package bean;

import db.AlunoEntityManager;
import db.DatabaseOperations;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

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
    
    public String atualizarAluno() {
        return DatabaseOperations.atualizarDadosAluno(this.id, this.nome);
    }
    
    public String editarAlunoPeloId() {
        // só redireciona com idAluno na URL
        return "editaraluno.xhtml?faces-redirect=true&idAluno=" + this.id;
    }
    
    public void carregarAluno(ComponentSystemEvent event) {
    AlunoEntityManager aluno = DatabaseOperations.getAlunoPorId(this.id);
    if (aluno != null) {
        this.nome = aluno.getNome();
    }
}
}
