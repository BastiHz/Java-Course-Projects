package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public UUID addCode(final Code code) {
        codeRepository.save(code);
        return code.getId();
    }

    public Code getCodeById(final UUID id) {
        final Code code = codeRepository.findById(id).orElseThrow();
        if (code.isInaccessible()) {
            throw new NoSuchElementException();
        }
        code.updateViews();
        codeRepository.save(code);  // update with new number of views left
        return code;
    }

    public List<Code> getLatestCodes() {
        return codeRepository.findTop10ByViewRestrictedFalseAndTimeRestrictedFalseOrderByDateDesc();
    }
}
