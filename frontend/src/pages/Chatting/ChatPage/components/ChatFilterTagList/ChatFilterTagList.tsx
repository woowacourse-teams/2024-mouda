import * as S from './ChatFilterTagList.styles';

import ChatFilterTag from '../ChatFilterTag/ChatFilterTag';
import { ReactElement } from 'react';

interface ChatFilterTagListProps {
  children:
    | ReactElement<typeof ChatFilterTag>
    | ReactElement<typeof ChatFilterTag>[];
}

export default function ChatFilterTagList(props: ChatFilterTagListProps) {
  const { children } = props;

  return <div css={S.list}>{children}</div>;
}
