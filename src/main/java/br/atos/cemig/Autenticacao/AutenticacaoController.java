package br.atos.cemig.Autenticacao;

import br.atos.cemig.Jwt.JwtAutenticaticacao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

    @PostMapping("/signup")
    @ResponseBody
    public JwtAutenticaticacao signup(@RequestBody Logout request) {

        return autenticacaoService.logout(request);
    }

    @PostMapping("/signin")
    @ResponseBody
    public JwtAutenticaticacao signin(@RequestBody Login request) {

        return autenticacaoService.login(request);
    }
}