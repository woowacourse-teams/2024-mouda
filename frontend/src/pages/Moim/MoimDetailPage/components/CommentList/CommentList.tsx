import * as S from './ComentList.style';

import { HTMLProps, useState } from 'react';

import { Comment } from '@_types/index';
import MessageInput from '@_components/Input/MessagInput/MessageInput';
import useWriteComment from '@_hooks/mutaions/useWriteComment';
import CommentCard from './CommentCard/CommentCard';

interface CommentListProps extends HTMLProps<HTMLDivElement> {
  moimId: number;
  comments: Comment[];
}

export default function CommentList(props: CommentListProps) {
  const { moimId, comments } = props;
  const { mutate: writeComment, isPending: isPendingWriteComment } =
    useWriteComment();
  const [selectedComment, setSelectedCommnet] = useState(0);

  return (
    <div css={S.commentListBox()}>
      {comments.map((comment) => {
        return (
          <CommentCard
            key={comment.commentId}
            comment={comment}
            onWriteClick={() => {
              if (comment.commentId === selectedComment) {
                setSelectedCommnet(0);
              } else {
                setSelectedCommnet(comment.commentId);
              }
            }}
            isChecked={comment.commentId === selectedComment}
          />
        );
      })}
      <MessageInput
        placeHolder={'메세지를 입력해주세요'}
        disabled={isPendingWriteComment}
        onSubmit={(message: string) =>
          writeComment({ moimId, message, selectedComment })
        }
      ></MessageInput>
    </div>
  );
}
