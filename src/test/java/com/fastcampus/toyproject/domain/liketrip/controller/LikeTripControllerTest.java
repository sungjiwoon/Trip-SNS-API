package com.fastcampus.toyproject.domain.liketrip.controller;
/*
import com.fastcampus.toyproject.config.security.jwt.UserPrincipal;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripRequest;
import com.fastcampus.toyproject.domain.liketrip.dto.LikeTripResponse;
import com.fastcampus.toyproject.domain.liketrip.service.LikeTripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class LikeTripControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikeTripService likeTripService;

    @InjectMocks
    private LikeTripController likeTripController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeTripController).build();
    }

    @Test
    public void testToggleLike() throws Exception {

        UserPrincipal mockPrincipal = Mockito.mock(UserPrincipal.class);
        Mockito.when(mockPrincipal.getUserId()).thenReturn(1L);


        Authentication authentication = new UsernamePasswordAuthenticationToken(mockPrincipal, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        LikeTripResponse likeTripResponse = new LikeTripResponse();
        likeTripResponse.setLikeTripId(1L);
        likeTripResponse.setTripId(1L);
        likeTripResponse.setIsLike(true);
        when(likeTripService.toggleLike(anyLong(), any(LikeTripRequest.class))).thenReturn(likeTripResponse);


        mockMvc.perform(post("/trip/1/like"))
            .andExpect(status().isOk());
    }
}
*/