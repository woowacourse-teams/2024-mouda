import { useState, useEffect } from 'react';

type StorageType = 'localStorage' | 'sessionStorage';

interface UseStatePersistParams<StateType> {
  key: string;
  initialState: StateType | (() => StateType);
  storage?: StorageType;
  removeOnUnmount?: boolean;
}

export default function useStatePersist<StateType>({
  key,
  initialState,
  storage = 'sessionStorage',
  removeOnUnmount = true,
}: UseStatePersistParams<StateType>): [
  StateType,
  React.Dispatch<React.SetStateAction<StateType>>,
] {
  const storageObject =
    storage === 'localStorage' ? window.localStorage : window.sessionStorage;

  const getStoredValue = (): StateType => {
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

  const [state, setState] = useState<StateType>(getStoredValue);

  const setStatePersist = (
    newState: StateType | ((prevState: StateType) => StateType),
  ) => {
    const updatedState =
      typeof newState === 'function'
        ? (newState as (prevState: StateType) => StateType)(state)
        : newState;

    setStoredValue(updatedState);
    setState(updatedState);
  };

  useEffect(() => {
    return () => {
      if (removeOnUnmount) {
        try {
          storageObject.removeItem(key);
        } catch (error) {
          console.error(`Error removing item from ${storage}`, error);
        }
      }
    };
  }, []);

  return [state, setStatePersist];
}
