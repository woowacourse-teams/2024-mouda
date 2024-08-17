import { useEffect, useRef, useState } from 'react';

export default function useStatePersist<StateType>({
  key,
  initialState,
}: {
  key: string;
  initialState: StateType | (() => StateType);
}): [StateType, React.Dispatch<React.SetStateAction<StateType>>] {
  const getStoredValue = (): StateType => {
    try {
      const item = sessionStorage.getItem(key);
      return item
        ? JSON.parse(item)
        : typeof initialState === 'function'
          ? (initialState as () => StateType)()
          : initialState;
    } catch (error) {
      console.error('Error reading from sessionStorage', error);
      return typeof initialState === 'function'
        ? (initialState as () => StateType)()
        : initialState;
    }
  };

  const [state, setState] = useState<StateType>(getStoredValue);
  const firstRender = useRef(true);

  useEffect(() => {
    if (firstRender.current) {
      firstRender.current = false;
      return;
    }

    try {
      sessionStorage.setItem(key, JSON.stringify(state));
    } catch (error) {
      console.error('Error writing to sessionStorage', error);
    }
  }, [key, state]);

  return [state, setState];
}
