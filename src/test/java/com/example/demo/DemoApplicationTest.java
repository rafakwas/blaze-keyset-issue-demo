package com.example.demo;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.blazebit.persistence.spring.data.repository.KeysetPageRequest;
import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.example.demo.repository.DocumentViewRepository;
import com.example.demo.repository.DocumentWithReaderViewRepository;
import com.example.demo.view.DocumentView;
import com.example.demo.view.DocumentWithReaderView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DemoApplicationTest {

    @Autowired
    private DocumentViewRepository documentViewRepository;
    @Autowired
    private DocumentWithReaderViewRepository documentWithReaderViewRepository;

    @Test // ok
    void given_keysetPageable_when_findAll_then_keysetPageNotNull() {
        // given
        var keysetPageable = defaultKeysetPageable();
        // when
        var actual = (KeysetAwarePage<DocumentView>)
                documentViewRepository.findAll(null, keysetPageable);
        // then
        assertThat(actual.getKeysetPage()).isNotNull();
    }

    @Test // ok
    void given_keysetPageable_when_findAll_then_mappingParameterNotSetAndKeysetPageNotNull() {
        // given
        var keysetPageable = defaultKeysetPageable();
        // when
        var actual = (KeysetAwarePage<DocumentWithReaderView>)
                documentWithReaderViewRepository.findAll(null, keysetPageable);
        //then
        assertThat(actual.getContent()).allMatch(documentWithReaderView -> documentWithReaderView.getReader() == null, "Optional parameter not set");
        assertThat(actual.getKeysetPage()).isNotNull();
    }

    @Test
    void given_optionalParameter_when_findAll_then_mappingParameterSetAndKeysetPageNotNull() {
        // given
        var keysetPageable = defaultKeysetPageable();
        var optionalParam = "someReader";
        // when
        var actual = (KeysetAwarePage<DocumentWithReaderView>)
                documentWithReaderViewRepository.findAll(null, keysetPageable, optionalParam); // fails because of null specification unlike previous tests
        //then
        assertThat(actual.getContent()).allMatch(documentWithReaderView -> documentWithReaderView.getReader().equals(optionalParam), "Optional parameter set");
        assertThat(actual.getKeysetPage()).isNotNull();
    }

    @Test
    void given_optionalParameter_when_findAllWithAlwaysTrueSpecs_then_mappingParameterSetAndKeysetPageNotNull() {
        // given
        var keysetPageable = defaultKeysetPageable();
        var optionalParam = "someReader";
        // when
        var actual = (KeysetAwarePage<DocumentWithReaderView>)
                documentWithReaderViewRepository.findAll(
                        (root, cq, cb) -> cb.conjunction(), // workaround for exception caused by null specs
                        keysetPageable,
                        optionalParam);
        //then
        assertThat(actual.getContent()).allMatch(documentWithReaderView -> documentWithReaderView.getReader().equals(optionalParam), "Optional parameter set");
        assertThat(actual.getKeysetPage()).isNotNull();
    }

    @Test
    void given_optionalParameter_when_findOne_then_mappingParameterSetAndKeysetPageNotNull() {
        // given
        var keysetPageable = defaultKeysetPageable();
        var optionalParam = "someReader";
        // when
        var actual = (KeysetAwarePage<DocumentWithReaderView>)
                documentWithReaderViewRepository.findAll(
                        (root, cq, cb) -> cb.and(cb.equal(root.get("id"), 1L)),
                        keysetPageable,
                        optionalParam);
        //then
        assertThat(actual.getTotalElements()).isEqualTo(1L);
        assertThat(actual.getContent()).hasSize(1);
        assertThat(actual.getKeysetPage()).isNotNull();
    }

    private static KeysetPageable defaultKeysetPageable() {
        return new KeysetPageRequest(null, Sort.by("id"), 0, 2, true, false);
    }

}
