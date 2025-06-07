package mju.nnews3.domain.api.controller;

import mju.nnews3.domain.api.dto.req.ViewReq;
import mju.nnews3.common.Response;
import mju.nnews3.domain.service.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
    private final ViewService viewService;

    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @PostMapping("/user/v1/news/view")
    public ResponseEntity<Response<Void>> updateView(
            @RequestBody ViewReq viewReq
    ) {
        viewService.updateView(viewReq);

        return ResponseEntity.ok(Response.success(null));
    }
}
