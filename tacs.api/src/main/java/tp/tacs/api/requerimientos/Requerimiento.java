package tp.tacs.api.requerimientos;

public abstract class Requerimiento <T, Y>{

        protected abstract Y execute(T request);

        public Y run(T request) {
            try {
                return execute(request);
            } catch (Exception e) {
                throw e;
        }
    }
}
