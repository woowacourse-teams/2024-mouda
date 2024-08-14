import * as S from './ErrorControlledInput.style';

import { ChangeEvent } from 'react';
import { useTheme } from '@emotion/react';

interface ErrorControlledInputProps {
  errorMessage: string;
  onChange: (string: string) => void;
  placeHolder?: string;
}

export default function ErrorControlledInput(props: ErrorControlledInputProps) {
  const { errorMessage, onChange, placeHolder } = props;
  const theme = useTheme();

  return (
    <div css={S.container}>
      <input
        type="text"
        onChange={(e: ChangeEvent<HTMLInputElement>) => {
          onChange(e.target.value);
          placeHolder;
        }}
        css={S.input({ theme })}
      />
      {errorMessage && (
        <span css={S.errorMessage({ theme })}>{errorMessage}</span>
      )}
    </div>
  );
}
