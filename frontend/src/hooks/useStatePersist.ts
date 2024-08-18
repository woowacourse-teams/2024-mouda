import { useEffect, useState } from 'react';
import {
  useBeforeUnload,
  useLocation,
  useNavigationType,
} from 'react-router-dom';

type StorageType = 'localStorage' | 'sessionStorage';

interface UseStatePersistParams<StateType> {
  key: string;
  initialState: StateType | (() => StateType);
  storage?: StorageType;
  clearStorageOnExit?: boolean;
}

export default function useStatePersist<StateType>({
  key,
  initialState,
  storage = 'sessionStorage',
  clearStorageOnExit = true,
}: UseStatePersistParams<StateType>): [
  StateType,
  React.Dispatch<React.SetStateAction<StateType>>,
] {
  const storageObject =
    storage === 'localStorage' ? window.localStorage : window.sessionStorage;

  const navigationType = useNavigationType();
  const location = useLocation();

  const getStoredValue = (): StateType => {
    if (location.state?.step === undefined && navigationType === 'PUSH') {
      return typeof initialState === 'function'
        ? (initialState as () => StateType)()
        : initialState;
    }

    try {
      const item = storageObject.getItem(key);
      if (item !== null) {
        return JSON.parse(item);
      }
    } catch (error) {
      console.error(`Error reading from ${storage}`, error);
    }

    return typeof initialState === 'function'
      ? (initialState as () => StateType)()
      : initialState;
  };

  const setStoredValue = (value: StateType) => {
    try {
      storageObject.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error(`Error writing to ${storage}`, error);
    }
  };

  const removeStoredValue = () => {
    try {
      storageObject.removeItem(key);
    } catch (error) {
      console.error(`Error removing item from ${storage}`, error);
    }
  };

  const [state, setState] = useState<StateType>(getStoredValue);

  const setStatePersist = (
    newState: StateType | ((prevState: StateType) => StateType),
  ) => {
    const updatedState =
      typeof newState === 'function'
        ? (newState as (prevState: StateType) => StateType)(state)
        : newState;

    // setStoredValue(updatedState);
    setState(updatedState);
  };

  useBeforeUnload(() => {
    if (clearStorageOnExit) {
      setStoredValue(state);
    }
  });

  useEffect(() => {
    removeStoredValue();
  }, []);

  return [state, setStatePersist];
}
