package br.com.myegoo.app.myego.service.login;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SegurancaService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ContaRepository contaRepository;
    @Autowired
    public SegurancaService(ContaRepository contaRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.contaRepository = contaRepository;
    }
    public void salvarConta(Conta conta) {
        contaRepository.save(conta);
    }
    public String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
