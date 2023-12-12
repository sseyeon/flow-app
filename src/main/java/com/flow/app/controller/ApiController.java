package com.flow.app.controller;

import com.flow.app.model.Extension;
import com.flow.app.model.request.ExtensionRequest;
import com.flow.app.service.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flow")
public class ApiController {
    @Autowired
    private ExtensionService extensionService;
    // 모든 확장자 조회
    @GetMapping("/extensions")
    public ResponseEntity<List<Extension>> getAllExtensions() {
        List<Extension> extensions = extensionService.getAllExtensions();
        return ResponseEntity.ok(extensions);
    }

    // 확장자 종류에 따른 조회 (고정/커스텀)
    @GetMapping("/extension/{type}")
    public List<Extension> getExtensionsByType(@PathVariable("type") String type) {
        if (type.equals("custom")) {
            return extensionService.getExtensionsByFixedStatus(Boolean.FALSE);
        } else {
            return extensionService.getExtensionsByFixedStatus(Boolean.TRUE);
        }
    }

    // 확장자 등록
   @PostMapping("/extension")
   public Extension createExtension(@RequestBody ExtensionRequest request) {
       return extensionService.addExtension(request.getExtension());
   }

    // 확장자 이름으로 확장자 조회
    @GetMapping("/extension")
    public Extension getExtensionByName(@RequestParam(name = "ext") String name) {
        return extensionService.getExtension(name);
    }


    // 확장자 삭제 (상태 변경)
    @PutMapping("/extension")
    public ResponseEntity<String> updateExtensionStatus(@RequestBody ExtensionRequest request) {
        extensionService.updateExtensionStatus(request.getExtension(), request.getStatus());
        return ResponseEntity.ok("확장자 상태가 성공적으로 변경되었습니다.");
    }


}
