import * as S from '@_components/Input/MessagInput/MessageInput.style';

import SubmitButton from '@_common/assets/submit_message_button.svg';
import { useState } from 'react';
import { useTheme } from '@emotion/react';

export interface MessageInputProps {
  placeHolder: string;
  disabled: boolean;
  onSubmit: (message: string) => void;
}

export default function MessageInput(props: MessageInputProps) {
  const { placeHolder, disabled, onSubmit } = props;
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
        disabled={!message.trim() || disabled}
        aria-label={`댓글쓰기 버튼 ${!message.trim() || disabled ? '댓글을 작성하여 버튼을 활성화하세요' : ''}`}
      >
        <SubmitButton />
      </button>
    </form>
  );
}
