package TourData.backend.domain.post.controller;

import TourData.backend.domain.post.dto.PostDto.PostResponse;
import TourData.backend.domain.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postSerivce;

    // 게시물 조회
    @GetMapping
    public List<PostResponse> getAllPosts(){
        return postSerivce.getAllPosts();
    }

}
