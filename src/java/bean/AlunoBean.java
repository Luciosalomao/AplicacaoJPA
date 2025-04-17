package bean;

import db.DatabaseOperations;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author lucio.souza
 */
@Named("alunoBean")
@RequestScoped
public class AlunoBean {
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
    
    public String atualizarAluno(AlunoBean alunoBean){
        return DatabaseOperations.atualizarDadosAluno(Integer.parseInt(alunoBean.getEditIdAluno()), alunoBean.getNome());
    }
    
    public String editarAlunoPeloId(){
        editIdAluno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("SELECT *FROM ALUNO;");
        return "editaraluno.xhtml";
    }
}
