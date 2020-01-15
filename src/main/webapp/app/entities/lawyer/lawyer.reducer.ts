import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILawyer, defaultValue } from 'app/shared/model/lawyer.model';

export const ACTION_TYPES = {
  FETCH_LAWYER_LIST: 'lawyer/FETCH_LAWYER_LIST',
  FETCH_LAWYER: 'lawyer/FETCH_LAWYER',
  CREATE_LAWYER: 'lawyer/CREATE_LAWYER',
  UPDATE_LAWYER: 'lawyer/UPDATE_LAWYER',
  DELETE_LAWYER: 'lawyer/DELETE_LAWYER',
  RESET: 'lawyer/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILawyer>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LawyerState = Readonly<typeof initialState>;

// Reducer

export default (state: LawyerState = initialState, action): LawyerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LAWYER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LAWYER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LAWYER):
    case REQUEST(ACTION_TYPES.UPDATE_LAWYER):
    case REQUEST(ACTION_TYPES.DELETE_LAWYER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LAWYER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LAWYER):
    case FAILURE(ACTION_TYPES.CREATE_LAWYER):
    case FAILURE(ACTION_TYPES.UPDATE_LAWYER):
    case FAILURE(ACTION_TYPES.DELETE_LAWYER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LAWYER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data.lawyers,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_LAWYER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LAWYER):
    case SUCCESS(ACTION_TYPES.UPDATE_LAWYER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LAWYER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/lawyers';

// Actions

export const getEntities: ICrudGetAllAction<ILawyer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LAWYER_LIST,
    payload: axios.get<ILawyer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILawyer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LAWYER,
    payload: axios.get<ILawyer>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILawyer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LAWYER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILawyer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LAWYER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILawyer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LAWYER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
