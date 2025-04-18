package db;

import db.AlunoEntityManager;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
/**
 *
 * @author lucio.souza
 */
public class DatabaseOperations {
    private static final String PERSISTENCE_UNIT_NAME = "AplicacaoJPAPU";
    private static EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static EntityTransaction transacao = entityManager.getTransaction();
    
    @SuppressWarnings("unchecked")
    public static List getDetalhesAlunos() {
        Query queryObj = entityManager.createQuery("SELECT s FROM AlunoEntityManager s");
        List listaAluno = queryObj.getResultList();
        if (listaAluno != null && listaAluno.size() > 0){
            return listaAluno;
        } else {
            return null;
        }
    }
    
    public static String cadastraNovoAluno(String nome) {
        if(!transacao.isActive()){
            transacao.begin();
        }
        
        AlunoEntityManager novoAluno = new AlunoEntityManager();
        novoAluno.setId(getMaxAlunoId());
        novoAluno.setNome(nome);
        entityManager.persist(novoAluno);
        transacao.commit();
        
        return "listaralunos.xhtml?faces-redirect=true";
        
    }
    
    public static String deleteAluno(int alunoId) {
        if(!transacao.isActive()){
            transacao.begin();
        }       
        
        AlunoEntityManager deleteAluno = new AlunoEntityManager();
        if(isAlunoId(alunoId)){
            deleteAluno.setId(alunoId);
            entityManager.remove(entityManager.merge(deleteAluno));
        }
        
        transacao.commit();
        
        return "listaralunos.xhtml?faces-redirect=true";
    }
    
    public static String atualizarDadosAluno(int alunoId, String atualizarNomeAluno){
        if(!transacao.isActive()){
            transacao.begin();
        }
        
        if(isAlunoId(alunoId)){
            Query queryObj = entityManager.createQuery("UPDATE AlunoEntityManager s SET s.nome=:nome WHERE s.id=:id");
            queryObj.setParameter("id", alunoId);
            queryObj.setParameter("nome", atualizarNomeAluno);
            int updateCount = queryObj.executeUpdate();            
            if(updateCount > 0) {
                System.out.println("Id: " + alunoId + " atualizado");
            }
        }
        transacao.commit();
        
        //return "editaraluno.xhtml";
        return "listaralunos.xhtml?faces-redirect=true";

    }
    
    public static boolean isAlunoId(int idAluno){
        boolean isResult = false;
        try {
            Query queryObj = entityManager.createQuery("SELECT s FROM AlunoEntityManager s WHERE s.id=:id");
            queryObj.setParameter("id", idAluno);
            AlunoEntityManager selecionarIdAluno = (AlunoEntityManager) queryObj.getSingleResult();
            if (selecionarIdAluno != null) {
                isResult = true;
            }
        } catch (jakarta.persistence.NoResultException e) {
            isResult = false; 
        }
        return isResult;
    }
   
   private static int getMaxAlunoId(){
       int contIdAluno = 1;
       Query queryObj = entityManager.createQuery("SELECT MAX(s.id)+1 FROM AlunoEntityManager s");
       if(queryObj.getSingleResult() != null){
           contIdAluno = (Integer) queryObj.getSingleResult();
       }
       return contIdAluno;
   }
   
   public static AlunoEntityManager getAlunoPorId(int id) {
    try {
        Query query = entityManager.createQuery("SELECT s FROM AlunoEntityManager s WHERE s.id = :id");
        query.setParameter("id", id);
        return (AlunoEntityManager) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
