package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.model.Categoria;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.TextField;

public class CategoriaService {
    public static void salvarCategoria(CategoriaRepository categoriaRepository, TextField nomeCategoria, String tipo) {
        Mensagem mensagem = new Mensagem();
        Categoria categoria = new Categoria(nomeCategoria.getText(), tipo);
        if(categoriaRepository.findByNome(categoria.getNome()) != null){
            mensagem.showMessege("Erro", "Nome da categoria já cadastrada!", 1);
            nomeCategoria.clear();
            throw new TratadorDeErros("Nome da categoria já cadastrada!");
        } else {
            mensagem.showMessege("Sucesso", "Categoria cadastrada com sucesso!", 2);
            categoriaRepository.save(categoria);
        }

    }
    public static void removerCategoria(CategoriaRepository categoriaRepository, String nomeCategoria,
                                        String tipo) {
        Mensagem mensagem = new Mensagem();
        int retorno = mensagem.retornoMessege("Remover","Deseja realmente remover " + nomeCategoria + "?");
        if (retorno == 1) {
            try {
                categoriaRepository.deleteByNomeAndTipo(nomeCategoria, tipo);
                new Mensagem().showMessege("Sucesso", "Categoria removida com sucesso!", 2);
            } catch (Exception e) {
                new Mensagem().showMessege("Erro", "Erro ao remover categoria!", 1);
            }
        }
    }
}
