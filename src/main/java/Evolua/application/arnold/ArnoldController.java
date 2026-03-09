package Evolua.application.arnold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Evolua.application.arnold.dto.ArnoldChatRequest;
import Evolua.application.arnold.dto.ArnoldResponse;
import Evolua.application.config.JWTUserData;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/arnold")
public class ArnoldController {
    
    @Autowired
    private ArnoldService service;

    @PostMapping("/chat")
    public ResponseEntity<ArnoldResponse> chat(@RequestBody @Valid ArnoldChatRequest request, @AuthenticationPrincipal JWTUserData usuarioLogado){

        System.out.println("mensagem" +  request.mensagem());
        ArnoldResponse response = service.interagir(request.mensagem(), usuarioLogado.usuarioId());
        return ResponseEntity.ok(response);
    }
}