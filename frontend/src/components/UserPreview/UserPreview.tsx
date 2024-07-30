// import Crown from '@_common/assets/crown.svg';

import { Fragment } from 'react';
import { preview } from './UserPreview.style';

interface UserPreviewProps {
  imageUrl?: string;
  size?: string;
  hasCrown?: boolean;
}

export default function UserPreview(props: UserPreviewProps) {
  const { imageUrl, size, hasCrown = false } = props;
  hasCrown;
  return (
    <Fragment>
      <div css={preview({ imageUrl, size })} />
    </Fragment>
  );
}
