import crypto from 'crypto';

export function passwordHash(toHash) {
  return crypto
  .createHash('sha256')
  .update(toHash)
  .digest('hex');
}
