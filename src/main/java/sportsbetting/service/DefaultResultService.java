package sportsbetting.service;

import sportsbetting.dao.ResultRepository;
import sportsbetting.model.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultResultService implements IResultService {

    private ResultRepository resultRepository;

    @Autowired
    public DefaultResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public Result save(Result result) {
        return resultRepository.save(result);
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

}
