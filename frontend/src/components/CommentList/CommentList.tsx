import CommentCard from '@_components/CommentCard/CommentCard';
import * as S from '@_components/CommentList/ComentList.style';
import { Comment } from '@_types/index';
import { HTMLProps } from 'react';

interface CommentListProps extends HTMLProps<HTMLDivElement> {
  comments: Comment[];
}

export default function CommentList(props: CommentListProps) {
  const { comments } = props;

  return (
    <div css={S.commentListBox()}>
      {comments.map((comment) => {
        return <CommentCard key={comment.id} comment={comment} />;
      })}
    </div>
  );
}
