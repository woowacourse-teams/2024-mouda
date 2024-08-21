import * as S from '@_components/Input/MessagInput/MessageInput.style';
import { useTheme } from '@emotion/react';

import { useState } from 'react';
import SubmitButton from '@_common/assets/submit_message_button.svg';

export interface MessageInputProps {
  placeHolder: string;
  onSubmit: (message: string) => void;
}

export default function MessageInput(props: MessageInputProps) {
  const { placeHolder, onSubmit } = props;
  const [message, setMessage] = useState('');

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(event.target.value);
  };
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    onSubmit(message);
    setMessage('');
  };
  const theme = useTheme();
  return (
    <form css={S.formBox({ theme })} onSubmit={handleSubmit}>
      <input
        css={S.input({ theme })}
        type="text"
        placeholder={placeHolder}
        value={message}
        onChange={handleChange}
      />
      <button
        css={S.button({ theme })}
        type="submit"
        disabled={!message.trim()}
      >
        <SubmitButton />
      </button>
    </form>
  );
}
