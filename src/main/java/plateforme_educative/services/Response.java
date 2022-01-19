package plateforme_educative.services;

public class Response<T> {
	 private T object;
	    private Status status;

	    public Response(T object, Status status) {
	        this.object = object;
	        this.status = status;
	    }

	    public enum Status {
	        CREATED,
	        NOT_CREATED,
	        RECEIVED,
	        NOT_RECEIVED
	    }

		public T getObject() {
			return object;
		}

		public void setObject(T object) {
			this.object = object;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
	    
}
